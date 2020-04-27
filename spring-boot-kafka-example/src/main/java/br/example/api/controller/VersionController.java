package br.example.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class VersionController {

	@Autowired
	BuildProperties buildProperties;

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain")
	public String getVersion() {

		StringBuilder build = new StringBuilder();

		// Artifact's name from the pom.xml file
		build.append(buildProperties.getName());

		build.append("\n");

		// Artifact version
		build.append(buildProperties.getVersion());

		build.append("\n");

		// Date and Time of the build
		build.append(buildProperties.getTime());

		return build.toString();

	}

}
