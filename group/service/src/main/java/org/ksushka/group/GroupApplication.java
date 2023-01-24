package org.ksushka.group;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.ecwid.consul.v1.agent.AgentConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;

import jakarta.annotation.PreDestroy;

@SpringBootApplication
public class GroupApplication {

    private AgentConsulClient agent = null;

    @Value("${server.port}")
	private int port;

	@Value("${spring.application.name}")
	private String applicationName;

    @Value("${consul-agent.host}")
    private String consulAgentHost;

    @Value("${consul-agent.port}")
	private int consulAgentPort;

    @EventListener(ApplicationReadyEvent.class)
	public void startup() {
        agent = new AgentConsulClient(consulAgentHost, consulAgentPort);

		NewService service = new NewService();
		service.setId(String.format("%s-%d", applicationName, port));
		service.setName(applicationName);
		service.setTags(new ArrayList<>());
		service.setAddress("localhost");
		HashMap<String, String> meta = new HashMap<>();
		meta.put("secure", "true");
		service.setMeta(meta);
		service.setPort(port);
		NewService.Check check = new NewService.Check();
		check.setInterval("10s");
		check.setHttp(String.format("https://localhost:%d/actuator/health", port));
		check.setHeader(new HashMap<>());
		check.setTlsSkipVerify(true);
		service.setCheck(check);

		agent.agentServiceRegister(service);
	}

    @PreDestroy
	public void destroy() {
		agent.agentServiceDeregister(String.format("%s-%d", applicationName, port));
	}

    public static void main(String[] args) {
        SpringApplication.run(GroupApplication.class, args);
    }

}
