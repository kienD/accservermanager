package grimsi.accservermanager.backend.service;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.*;
import grimsi.accservermanager.backend.configuration.ApplicationConfiguration;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.exception.ContainerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContainerService {

    private final DockerClient docker;
    @Autowired
    InstanceService instanceService;
    @Autowired
    FileSystemService fileSystemService;
    @Autowired
    Environment env;
    @Autowired
    ApplicationConfiguration config;
    private Logger log = LoggerFactory.getLogger(ContainerService.class);

    @Autowired
    public ContainerService(UtilityService utilityService) {
        switch (utilityService.getHostOS()) {
            case WINDOWS:
                docker = new DefaultDockerClient("http://localhost:2375");
                break;
            case UNIX:
                docker = new DefaultDockerClient("unix:///var/run/docker.sock");
                break;
            case MAC:
                docker = new DefaultDockerClient("unix:///var/run/docker.sock");
                break;
            default:
                docker = new DefaultDockerClient("unix:///var/run/docker.sock");
                break;
        }
    }

    public List<Container> getAllContainers() {
        try {
            return docker.listContainers(DockerClient.ListContainersParam.allContainers());
        } catch (DockerException | InterruptedException e) {
            throw new ContainerException("Could not get a list of containers: " + e.getMessage());
        }
    }

    public void deployInstance(InstanceDto instance) {

        String[] ports = {
                (instance.getConfig().getConfigurationJson().getTcpPort() + "/tcp"),
                (instance.getConfig().getConfigurationJson().getUdpPort() + "/udp")
        };

        Map<String, List<PortBinding>> portBindings = new HashMap<>();
        for (String port : ports) {
            portBindings.put(port, Collections.singletonList(PortBinding.of("0.0.0.0", port)));
        }

        HostConfig hostConfig = HostConfig.builder()
                .portBindings(portBindings)
                .binds(fileSystemService.getInstanceFolderPath(instance) + ":/opt/server")
                .build();

        try {
            List<Image> images = docker.listImages().parallelStream().filter(
                    image -> image.repoTags().parallelStream()
                            .anyMatch(tag -> tag.equals(config.getContainerImage())
                            )
            ).collect(Collectors.toList());

            if (images.isEmpty()) {
                docker.pull(config.getContainerImage());
            }
        } catch (DockerException | InterruptedException e) {
            throw new ContainerException("Could not find/pull image '" + config.getContainerImage() + "': " + e.getMessage());
        }

        ContainerConfig containerConfig = ContainerConfig.builder()
                .hostConfig(hostConfig)
                .image(config.getContainerImage())
                .exposedPorts(ports)
                .build();

        try {
            String containerName = buildContainerName(instance);
            ContainerCreation container = docker.createContainer(containerConfig, containerName);

            instance.setContainer(container.id());
            instanceService.save(instance);

        } catch (DockerException | InterruptedException e) {
            throw new ContainerException("Could not create container for instance '" + instance.getId() + "': " + e.getMessage());
        }
    }

    public void startInstance(InstanceDto instance) {
        try {
            docker.startContainer(instance.getContainer());
        } catch (DockerException | InterruptedException e) {
            throw new ContainerException("Could not start container '" + instance.getContainer() + "': " + e.getMessage());
        }
    }

    public void stopInstance(InstanceDto instance) {
        try {
            docker.stopContainer(instance.getContainer(), 0);
        } catch (DockerException | InterruptedException e) {
            throw new ContainerException("Could not stop container '" + instance.getContainer() + "': " + e.getMessage());
        }
    }

    public void pauseInstance(InstanceDto instance) {
        try {
            docker.pauseContainer(instance.getContainer());
        } catch (DockerException | InterruptedException e) {
            throw new ContainerException("Could not pause container '" + instance.getContainer() + "': " + e.getMessage());
        }
    }

    public void resumeInstance(InstanceDto instance) {
        try {
            docker.unpauseContainer(instance.getContainer());
        } catch (DockerException | InterruptedException e) {
            throw new ContainerException("Could not resume container '" + instance.getContainer() + "': " + e.getMessage());
        }
    }

    public void deleteInstance(InstanceDto instance) {
        deleteContainer(instance.getContainer());
    }

    public void deleteContainer(String id) {
        try {
            docker.stopContainer(id, 0);
            docker.removeContainer(id);
        } catch (DockerException | InterruptedException | NullPointerException e) {
            throw new ContainerException("Cant delete container '" + id + "': " + e.getMessage());
        }
    }

    public boolean instanceHasContainer(InstanceDto instance) {
        try {
            return docker.listContainers().parallelStream().allMatch(
                    container -> (
                            container.id().equals(instance.getContainer())
                    )
            );
        } catch (DockerException | InterruptedException e) {
            throw new ContainerException("Could not get list of containers.");
        }
    }

    public boolean isContainerNameInUse(InstanceDto instanceDto) {
        String containerName = buildContainerName(instanceDto);

        /* Check if any active container has this name */
        return getAllContainers().parallelStream().anyMatch(
                container -> container.names().get(0).substring(1).equals(containerName)
        );
    }

    public ContainerStats getContainerStats(InstanceDto instance) {
        try {
            return docker.stats(instance.getContainer());
        } catch (DockerException | InterruptedException e) {
            log.error(e.toString());
        }
        return null;
    }

    public String buildContainerName(InstanceDto instance) {
        /* main part */
        String name = instance.getName();

        /* Postfix */
        if (config.isContainerNamePostfixEnabled()) {
            name = name.concat("-v").concat(instance.getVersion());
        }

        return name;
    }
}
