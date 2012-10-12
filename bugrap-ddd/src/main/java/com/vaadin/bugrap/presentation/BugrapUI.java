package com.vaadin.bugrap.presentation;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletException;

import com.vaadin.bugrap.business.reporter.ReporterBoundary;
import com.vaadin.bugrap.business.users.entity.Reporter;
import com.vaadin.bugrap.presentation.login.LoginEvent;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.VaadinUI;
import com.vaadin.cdi.component.JaasTools;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

@VaadinUI
public class BugrapUI extends UI {

    @Inject
    private CDIViewProvider viewProvider;

    private Navigator navigator;

    @Inject
    private ReporterBoundary reporterBoundary;

    @Override
    protected void init(VaadinRequest request) {
        setSizeFull();

        BugrapViewDisplay viewDisplay = new BugrapViewDisplay();
        setContent(viewDisplay);

        navigator = new Navigator(this, (ViewDisplay) viewDisplay);
        navigator.addProvider(viewProvider);

        if (JaasTools.isUserSignedIn()) {
            navigator.navigateTo("reports");
        } else {
            navigator.navigateTo("login");
        }
    }

    protected void onLogin(@Observes LoginEvent loginEvent) {
        try {
            JaasTools.login(loginEvent.getUsername(), loginEvent.getPassword());

            if (JaasTools.isUserSignedIn()) {
                if (!reporterBoundary.reporterExists(loginEvent.getUsername())) {
                    reporterBoundary
                            .createNewReporter(loginEvent.getUsername());
                }
                Reporter reporter = reporterBoundary.getReporter(loginEvent
                        .getUsername());
                getSession().setAttribute(Reporter.class, reporter);
                navigator.navigateTo("reports");
            }
        } catch (ServletException e) {
            Notification.show("Error logging in", Type.ERROR_MESSAGE);
        }

    }
}
