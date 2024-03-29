package com.vmware.springbootmaven;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(MessageApiController.BASE_URI)
public class MessageApiController {

	private static final Logger LOG = LoggerFactory.getLogger(MessageApiController.class);

	static final String BASE_URI = "/api";

	@Value("${configfrom:Hardcoded}")
	String configfrom;

	@Value("${client:VMware}")
	String client;

	@Value("${framework:Spring}")
	String framework;

	@Value("${message:Secure Software Supply Chains Are Great!}")
	String msgSubject;

	@Value("${msg_body:Message Body Text Here.}")
	String msgBody;

	@CrossOrigin()
	@Operation(summary = "Get a list of messages.",
	        description = "The API returns a list of messages. The content in these messages could come from a number or different configuration sources.",
			tags = {"Messages"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", 
			             description = "Messages were resolved and are being returned.",
						 content = @Content(schema = @Schema(name = "messages", description = "A map of messages and their subjects.", type = "map", example = "{\"subject a\": \"message\",\"subject b\": \"message\"}")))
	})
	@GetMapping(value = "/messages", produces = {"application/json"})
	public ResponseEntity<Map<String, String>> messages() {
		LOG.info("A request has been received for the /messages endpoint.");
		LOG.debug("Config is coming from {}", configfrom);
		Map<String, String> data = new HashMap<String, String>();
		data.put("msg_subject", msgSubject);
		data.put("msg_body", msgBody);
		data.put("framework", framework);
		data.put("extra_message", "extra message");
		data.put("client", client);
		data.put("config_from", configfrom);
		LOG.debug("Returning {}.", data.toString());
		return ResponseEntity.ok(data) ;
	}
}