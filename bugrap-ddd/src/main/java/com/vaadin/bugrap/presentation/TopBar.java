package com.vaadin.bugrap.presentation;

import java.util.List;

import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.bugrap.business.users.entity.Reporter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class TopBar extends CustomComponent {

	private final VerticalLayout layout;

	private final HorizontalLayout topLayout;
	private final HorizontalLayout bottomLayout;

	private final ComboBox projectSelector;

	private final Button userButton;
	private final Button logoutButton;

	private final Button reportBug;
	private final Button requestFeature;
	private final Button manageProject;

	private final TextField searchArea;

	private final ValueChangeListener projectChangeListener = new ValueChangeListener() {

		@Override
		public void valueChange(ValueChangeEvent event) {
		}
	};

	private final Button.ClickListener userListener = new Button.ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
		}
	};

	private final Button.ClickListener logoutListener = new Button.ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
		}
	};

	private final Button.ClickListener reportBugListener = new Button.ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
		}
	};

	private final Button.ClickListener featureListener = new Button.ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
		}
	};

	private final Button.ClickListener manageProjectListener = new Button.ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
		}
	};

	public TopBar() {
		layout = new VerticalLayout();
		layout.setSpacing(true);

		topLayout = new HorizontalLayout();
		topLayout.setSpacing(true);
		topLayout.setWidth(100, Unit.PERCENTAGE);

		bottomLayout = new HorizontalLayout();
		bottomLayout.setSpacing(true);
		bottomLayout.setWidth(100, Unit.PERCENTAGE);

		projectSelector = new ComboBox();
		projectSelector.setWidth(100, Unit.PERCENTAGE);

		userButton = new Button("", userListener);
		logoutButton = new Button("Logout", logoutListener);

		topLayout.addComponent(projectSelector);

		topLayout.addComponent(userButton);
		topLayout.addComponent(logoutButton);
		topLayout.setExpandRatio(projectSelector, 1);

		reportBug = new Button("Report a bug", reportBugListener);
		requestFeature = new Button("Request a feature", featureListener);
		manageProject = new Button("Manage project", manageProjectListener);

		searchArea = new TextField();
		searchArea.setWidth(250, Unit.PIXELS);

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);

		buttonLayout.addComponent(reportBug);
		buttonLayout.addComponent(requestFeature);
		buttonLayout.addComponent(manageProject);

		bottomLayout.addComponent(buttonLayout);
		bottomLayout.addComponent(searchArea);
		bottomLayout.setComponentAlignment(searchArea, Alignment.BOTTOM_RIGHT);

		layout.addComponent(topLayout);
		layout.addComponent(bottomLayout);

		setCompositionRoot(layout);
	}

	public void populateProjects(List<Project> projects) {
		projectSelector.removeListener(projectChangeListener);

		BeanItemContainer<Project> projectsContainer = new BeanItemContainer<Project>(
				Project.class);
		projectsContainer.addAll(projects);

		projectSelector.setContainerDataSource(projectsContainer);
		projectSelector
				.setItemCaptionPropertyId(Project.PROJECT_NAME_CAPTION_PROPERTY);

		projectSelector.addListener(projectChangeListener);
	}

	public void populateCurrentUser(Reporter user) {
		userButton.setCaption(user.getName());
	}

	public Project getSelectedProject() {
		return (Project) projectSelector.getValue();
	}
}
