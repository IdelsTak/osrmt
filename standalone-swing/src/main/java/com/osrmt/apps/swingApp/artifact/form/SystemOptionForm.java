package com.osrmt.apps.swingApp.artifact.form;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.*;
import java.text.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.jgoodies.forms.builder.*;
import com.jgoodies.forms.layout.*;

import com.osframework.appclient.ui.common.*;
import com.osframework.appclient.ui.components.*;
import com.osframework.appclient.ui.controls.*;
import com.osframework.modellibrary.common.*;
import com.osframework.modellibrary.reference.security.*;
import com.osframework.appclient.services.*;
import com.osframework.appclient.ui.listeners.*;
import com.osframework.modellibrary.reference.common.*;
import com.osframework.modellibrary.reference.group.*;
import com.osframework.framework.logging.*;
import com.osframework.framework.utility.*;
import com.osrmt.apps.swingApp.services.RequirementServices;
import com.osrmt.apps.swingApp.setting.*;
import com.osrmt.modellibrary.reference.group.ApplicationGroup;
import com.osrmt.modellibrary.reqmanager.*;

public class SystemOptionForm extends UIStandardDialog {
	
	private static final long serialVersionUID = 1L;

	private JFrame frame;
	private SystemOptionModel optionModel = null;
	
	public SystemOptionForm(JFrame frame) {
		super(frame);
		this.frame = frame;
		initialize();
		addListeners();
	}	
	
	public void initialize() {
		optionModel = new SystemOptionModel(SecurityServices.getGlobalSettings());
		setTitle(ReferenceServices.getDisplay(FormTitleFramework.SYSTEMOPTIONMAINTENANCE));
		ApplicationControlList acl = SecurityServices.getAppControls(ApplicationFramework.get(ApplicationGroup.SYSTEMOPTIONFORM));
		JPanel panel = ControlPanel.getPanel(frame, acl, optionModel, 1);
		getCenterPanel().add(panel, BorderLayout.CENTER);
	}
	
	
	private void addListeners() {
		getButtonPanel().getCmdButton0().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				dispose();
			}
		});
		// OK
		getButtonPanel().getCmdButton1().addActionListener(new UIActionListener(frame){
			public void actionExecuted(ActionEvent e) throws Exception {
				SecurityServices.UpdateApplicationSetting(optionModel);
				TimedAction ta = new TimedAction(1.0) {
					@Override
					public void executeTask() {
						AuthenticationSetting.initialize();
						DataFormatSetting.initialize();
					}
				};
				dispose();
			}
		});
	}
	
}

