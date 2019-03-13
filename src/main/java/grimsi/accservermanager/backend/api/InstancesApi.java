/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.5).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package grimsi.accservermanager.backend.api;

import grimsi.accservermanager.backend.dto.ConfigDto;
import grimsi.accservermanager.backend.dto.ErrorDto;
import grimsi.accservermanager.backend.dto.InstanceDto;
import grimsi.accservermanager.backend.enums.InstanceState;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "grimsi.accservermanager.backend.codegen.v3.generators.java.SpringCodegen", date = "2019-03-10T17:37:16.729Z[GMT]")
@Api(value = "instances", description = "the instances API")
public interface InstancesApi {

    @ApiOperation(value = "Create a instance", nickname = "createInstance", notes = "", response = ConfigDto.class, authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "the created instance", response = ConfigDto.class),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/instances",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<InstanceDto> createInstance(@ApiParam(value = "A JSON object containing the instance", required = true) @Valid @RequestBody InstanceDto body);


    @ApiOperation(value = "Delete a specific instance", nickname = "deleteInstanceById", notes = "", authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Empty response"),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/instances/{instanceId}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteInstanceById(@ApiParam(value = "The id of the instance to delete", required = true) @PathVariable("instanceId") String instanceId);


    @ApiOperation(value = "Get a specific instance", nickname = "getInstanceById", notes = "", response = InstanceDto.class, authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Expected response to a valid request", response = InstanceDto.class),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/instances/{instanceId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<InstanceDto> getInstanceById(@ApiParam(value = "The id of the instance to retrieve", required = true) @PathVariable("instanceId") String instanceId);


    @ApiOperation(value = "List all instances", nickname = "listInstances", notes = "", response = ArrayList.class, authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "An array of instances", response = ArrayList.class),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/instances",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<InstanceDto>> listInstances(@ApiParam(value = "Filter by name") @Valid @RequestParam(value = "name", required = false) String name, @ApiParam(value = "") @Valid @RequestParam(value = "state", required = false) InstanceState state);


    @ApiOperation(value = "Start a specific instance", nickname = "startInstanceById", notes = "", authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Expected response to a valid request"),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/instances/{instanceId}/start",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Void> startInstanceById(@ApiParam(value = "The id of the instance to start", required = true) @PathVariable("instanceId") String instanceId);


    @ApiOperation(value = "Stop a specific instance", nickname = "stopInstanceById", notes = "", authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Expected response to a valid request"),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/instances/{instanceId}/stop",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Void> stopInstanceById(@ApiParam(value = "The id of the instance to stop", required = true) @PathVariable("instanceId") String instanceId);


    @ApiOperation(value = "Update a instance", nickname = "updateInstanceById", notes = "", response = InstanceDto.class, authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "the created configuration", response = InstanceDto.class),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/instances/{instanceId}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<InstanceDto> updateInstanceById(@ApiParam(value = "A JSON object containing the instance", required = true) @Valid @RequestBody InstanceDto body, @ApiParam(value = "The id of the instance to retrieve", required = true) @PathVariable("instanceId") String instanceId);

    @ApiOperation(value = "Get the instance schema", nickname = "getInstanceSchema", notes = "", response = String.class, authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "the created configuration", response = String.class),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/instances/schema",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<String> getInstanceSchema();

}
