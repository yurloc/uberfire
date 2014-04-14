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

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CellTable;
import com.github.gwtbootstrap.client.ui.FluidContainer;
import com.github.gwtbootstrap.client.ui.Label;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.resources.ButtonSize;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.uberfire.client.common.ButtonCell;
import org.uberfire.client.tables.ResizableHeader;
import org.uberfire.commons.validation.PortablePreconditions;
import org.uberfire.user.management.client.resources.i18n.UserManagementConstants;
import org.uberfire.user.management.client.utils.UserManagementUtils;
import org.uberfire.user.management.model.UserInformation;

public class UserManagementViewImpl extends Composite implements UserManagementView {

    private UserManagementPresenter presenter;

    interface ViewBinder
            extends
            UiBinder<Widget, UserManagementViewImpl> {

    }

    @UiField(provided = true)
    CellTable<UserInformation> table = new CellTable<UserInformation>();

    @UiField
    FluidContainer container;

    @UiField
    Button addUserButton;

    private boolean isReadOnly = false;
    private ButtonCell deleteUserButton;
    private ButtonCell editUserButton;

    private static ViewBinder uiBinder = GWT.create( ViewBinder.class );

    @PostConstruct
    public void init() {
        initWidget( uiBinder.createAndBindUi( this ) );

        //Setup table
        table.setEmptyTableWidget( new Label( UserManagementConstants.INSTANCE.noUsersDefined() ) );

        //Columns
        final TextColumn<UserInformation> userNameColumn = new TextColumn<UserInformation>() {

            @Override
            public String getValue( final UserInformation userInformation ) {
                return userInformation.getUserName();
            }
        };

        final TextColumn<UserInformation> userRolesColumn = new TextColumn<UserInformation>() {

            @Override
            public String getValue( final UserInformation userInformation ) {
                return UserManagementUtils.convertUserRoles( userInformation.getUserRoles() );
            }

        };

        editUserButton = new ButtonCell( ButtonSize.SMALL );
        editUserButton.setType( ButtonType.DEFAULT );
        editUserButton.setIcon( IconType.EDIT );
        final Column<UserInformation, String> editUserColumn = new Column<UserInformation, String>( editUserButton ) {
            @Override
            public String getValue( final UserInformation userInformation ) {
                return UserManagementConstants.INSTANCE.edit();
            }
        };
        editUserColumn.setFieldUpdater( new FieldUpdater<UserInformation, String>() {
            public void update( final int index,
                                final UserInformation userInformation,
                                final String value ) {
                if ( isReadOnly ) {
                    return;
                }
                presenter.editUser( userInformation );
            }
        } );

        deleteUserButton = new ButtonCell( ButtonSize.SMALL );
        deleteUserButton.setType( ButtonType.DANGER );
        deleteUserButton.setIcon( IconType.MINUS_SIGN );
        final Column<UserInformation, String> deleteUserColumn = new Column<UserInformation, String>( deleteUserButton ) {
            @Override
            public String getValue( final UserInformation userInformation ) {
                return UserManagementConstants.INSTANCE.remove();
            }
        };
        deleteUserColumn.setFieldUpdater( new FieldUpdater<UserInformation, String>() {
            public void update( final int index,
                                final UserInformation userInformation,
                                final String value ) {
                if ( isReadOnly ) {
                    return;
                }
                if ( Window.confirm( UserManagementConstants.INSTANCE.promptForRemovalOfUser0( userInformation.getUserName() ) ) ) {
                    presenter.deleteUser( userInformation );
                }
            }
        } );

        table.addColumn( userNameColumn,
                         new ResizableHeader( UserManagementConstants.INSTANCE.userName(),
                                              table,
                                              userNameColumn ) );
        table.addColumn( userRolesColumn,
                         new ResizableHeader( UserManagementConstants.INSTANCE.userRoles(),
                                              table,
                                              userRolesColumn ) );
        table.addColumn( editUserColumn,
                         UserManagementConstants.INSTANCE.edit() );
        table.addColumn( deleteUserColumn,
                         UserManagementConstants.INSTANCE.remove() );
    }

    @Override
    public void init( final UserManagementPresenter presenter ) {
        this.presenter = PortablePreconditions.checkNotNull( "presenter",
                                                             presenter );
    }

    @Override
    public void setContent( final List<UserInformation> userInformation,
                            final boolean isReadOnly ) {
        this.isReadOnly = isReadOnly;
        this.table.setRowData( userInformation );
        addUserButton.setEnabled( !isReadOnly );
        editUserButton.setEnabled( !isReadOnly );
        deleteUserButton.setEnabled( !isReadOnly );
    }

    @UiHandler(value = "addUserButton")
    public void onClickAddUserButton( final ClickEvent event ) {
        presenter.addUser();
    }

}
