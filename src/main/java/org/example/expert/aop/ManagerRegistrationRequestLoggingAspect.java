package org.example.expert.aop;

import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.manager.entity.ManagerSaveLog;
import org.example.expert.domain.manager.entity.ManagerSaveLogStatus;
import org.example.expert.domain.manager.service.ManagerLogService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ManagerRegistrationRequestLoggingAspect {

	private final HttpServletRequest request;
	private final ManagerLogService managerLogService;

	@Around("execution(* org.example.expert.domain.manager.service.ManagerService.saveManager(..))")
	public Object logAfterChangeUserRole(ProceedingJoinPoint joinPoint) throws Throwable {
		AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String requestUrl = request.getRequestURI();
		LocalDateTime requestTime = LocalDateTime.now();
		ManagerSaveLogStatus status = ManagerSaveLogStatus.SUCCESS;
		Object result = null;
		try {
			result = joinPoint.proceed();
			return result;
		} catch (Throwable e) {
			log.error(e.getMessage());
			status = ManagerSaveLogStatus.FAIL;
			throw e;
		} finally {
			log.info("Admin Access Log - User Id: {}, Request Time: {}, Request URL: {}, Method: {}, Status: {}",
				authUser.getId(), requestTime, requestUrl, joinPoint.getSignature().getName(), status.name());
			managerLogService.saveLog(ManagerSaveLog.builder()
				.requestUserId(authUser.getId())
				.createdAt(requestTime)
				.status(status)
				.build());
		}
	}
}
