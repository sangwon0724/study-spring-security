package com.example.demo.controller;

import com.example.demo.domain.SessionInfo;
import com.example.demo.domain.UserSession;
import com.example.demo.user.domain.SpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

@Controller
public class SessionController {

    @Autowired
    private SessionRegistry sessionRegistry;

    //세션 목록 조회
    @GetMapping("/sessions")
    public String sessions(Model model){
        model.addAttribute(
        		"sessionList",
                sessionRegistry
                .getAllPrincipals()
                .stream()
                .map(
                		p->	UserSession.builder()
                			.username(((SpUser)p).getUsername())
                			.sessions(
                				sessionRegistry
                				.getAllSessions(p, false)
                				.stream()
                				.map(
                						s->	SessionInfo
                							.builder()
                                        	.sessionId(s.getSessionId())
                                        	.time(s.getLastRequest())
                                        	.build()
                                )
                                .collect(Collectors.toList())
                             )
                        	.build()
                ).collect(Collectors.toList())
        );
        return "/sessionList";
    }

    //세션 만료
    @PostMapping("/session/expire")
    public String expireSession(@RequestParam String sessionId){
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        if(!sessionInformation.isExpired()){
            sessionInformation.expireNow();
        }
        return "redirect:/sessions";
    }

    //세션 만료
    @GetMapping("/session-expired")
    public String sessionExpired(){
        return "/sessionExpired";
    }

}
