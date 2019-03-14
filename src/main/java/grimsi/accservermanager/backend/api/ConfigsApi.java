/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.5).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package grimsi.accservermanager.backend.api;

import grimsi.accservermanager.backend.dto.ConfigDto;
import grimsi.accservermanager.backend.dto.ErrorDto;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@javax.annotation.Generated(value = "grimsi.accservermanager.backend.codegen.v3.generators.java.SpringCodegen", date = "2019-03-10T17:37:16.729Z[GMT]")
@Api(value = "configs", description = "the configs API")
public interface ConfigsApi {

    @ApiOperation(value = "Create a configuration", nickname = "createConfig", notes = "", response = ConfigDto.class, authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "the created configuration", response = ConfigDto.class),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/configs",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<ConfigDto> createConfig(@ApiParam(value = "A JSON object containing the configuration", required = true) @Valid @RequestBody ConfigDto body);


    @ApiOperation(value = "Delete a specific configuration", nickname = "deleteConfigById", notes = "", authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Empty response"),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/configs/{configId}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteConfigById(@ApiParam(value = "The id of the configuration to delete", required = true) @PathVariable("configId") String configId);


    @ApiOperation(value = "Get a specific configuration", nickname = "getConfigById", notes = "", response = ConfigDto.class, authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Expected response to a valid request", response = ConfigDto.class),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/configs/{configId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<ConfigDto> getConfigById(@ApiParam(value = "The id of the configuration to retrieve", required = true) @PathVariable("configId") String configId);


    @ApiOperation(value = "List all configurations", nickname = "listConfigs", notes = "", response = List.class, authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A array of configurations", response = List.class),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/configs",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<ConfigDto>> listConfigs(@ApiParam(value = "Filter by name") @Valid @RequestParam(value = "name", required = false) String name);


    @ApiOperation(value = "Update a configuration", nickname = "updateConfigById", notes = "", response = ConfigDto.class, authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "the created configuration", response = ConfigDto.class),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/configs/{configId}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<ConfigDto> updateConfigById(@ApiParam(value = "A JSON object containing the configuration", required = true) @Valid @RequestBody ConfigDto body, @ApiParam(value = "The id of the configuration to retrieve", required = true) @PathVariable("configId") String configId);

    @ApiOperation(value = "Get the configuration schema", nickname = "getConfigSchema", notes = "", response = String.class, authorizations = {
            @Authorization(value = "auth")}, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "the created configuration", response = String.class),
            @ApiResponse(code = 500, message = "unexpected error", response = ErrorDto.class)})
    @RequestMapping(value = "/configs/schema",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<String> getConfigSchema();
}
