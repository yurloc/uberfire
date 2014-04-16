/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.uberfire.user.management.client;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.commons.validation.PortablePreconditions;
import org.uberfire.user.management.model.UserInformation;
import org.uberfire.user.management.model.UserManagerCapabilities;
import org.uberfire.user.management.model.UserManagerContent;

public class UserManagementViewImpl extends Composite implements RequiresResize,
                                                                 UserManagementView {

    interface UserManagementViewImplBinder
            extends
            UiBinder<Widget, UserManagementViewImpl> {

    }

    private static UserManagementViewImplBinder uiBinder = GWT.create( UserManagementViewImplBinder.class );

    @Inject
    private UserManagerWidget userManagerWidget;

    @Inject
    private NoUserManagerInstalledWidget noUserManagerInstalledWidget;

    @UiField
    SimplePanel container;


    private UserManagementPresenter presenter;

    @PostConstruct
    public void init() {
        initWidget( uiBinder.createAndBindUi( this ) );
    }

    @Override
    public void setUserManagerInstalled( final boolean isUserManagerInstalled ) {
        container.clear();
        if ( isUserManagerInstalled ) {
            container.setWidget( userManagerWidget );
            presenter.loadContent();

        } else {
            container.setWidget( noUserManagerInstalledWidget );
            onResize();
        }
    }

    @Override
    public void init( final UserManagementPresenter presenter ) {
        userManagerWidget.init( presenter );
        this.presenter = PortablePreconditions.checkNotNull( "presenter",
                                                             presenter );
    }

    @Override
    public void setContent( final UserManagerContent content,
                            final boolean isReadOnly ) {
        userManagerWidget.setContent( content,
                                      isReadOnly );
    }

    @Override
    public void onResize() {
        if ( getParent() == null ) {
            return;
        }
        final IsWidget child = container.getWidget();
        if ( !( child instanceof RequiresResize ) ) {
            return;
        }
        int height = getParent().getOffsetHeight();
        int width = getParent().getOffsetWidth();
        container.setPixelSize( width,
                                height );
        ( (RequiresResize) child ).onResize();
    }

}
