package com.mfc.mds.consolidation.web.config;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

import org.jboss.logging.Logger;
import org.omnifaces.util.Faces;

public class AuthPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 283329993290834258L;
	private static final Logger logger = Logger.getLogger(AuthPhaseListener.class);
	
	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}
	
	@Override
	public void afterPhase(PhaseEvent arg0) { }

	@Override
	public void beforePhase(PhaseEvent arg0) {
		validateUser();
	}
	
	private void validateUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletRequest servletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		
		if(!servletRequest.getRequestURI().toString().equals("/login/index.xhtml") &&
				UserSession.getCurrentInstance().getUser() == null){
			try {
				Faces.redirect("/login/index.xhtml");
			} catch (IOException e) {
				logger.error("Error while redirecting to /login/index.xhtml. " + e.getMessage(), e);
			}
		}
	}

}
