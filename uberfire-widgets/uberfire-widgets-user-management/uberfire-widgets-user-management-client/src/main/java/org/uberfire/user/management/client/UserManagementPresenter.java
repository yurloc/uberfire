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
import javax.inject.Inject;

import com.google.gwt.user.client.Command;
import org.jboss.errai.bus.client.api.messaging.Message;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.ErrorCallback;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;
import org.uberfire.client.common.popups.errors.ErrorPopup;
import org.uberfire.client.mvp.UberView;
import org.uberfire.lifecycle.OnStartup;
import org.uberfire.mvp.PlaceRequest;
import org.uberfire.user.management.client.popups.AddUserPopup;
import org.uberfire.user.management.client.popups.EditUserPopup;
import org.uberfire.user.management.client.resources.i18n.UserManagementConstants;
import org.uberfire.user.management.client.utils.UserManagementUtils;
import org.uberfire.user.management.model.UserInformation;
import org.uberfire.user.management.model.UserInformationWithPassword;
import org.uberfire.user.management.service.UserManagementService;

@WorkbenchScreen(identifier = "UserManagementPresenter")
public class UserManagementPresenter {

    @Inject
    private UserManagementView view;

    @Inject
    private AddUserPopup addUserPopup;

    @Inject
    private EditUserPopup editUserPopup;

    @Inject
    private Caller<UserManagementService> userManagementService;

    private PlaceRequest place;
    private boolean isReadOnly;

    @OnStartup
    public void onStartup( final PlaceRequest place ) {
        this.place = place;
        this.isReadOnly = place.getParameter( "readOnly", null ) == null ? false : true;

        userManagementService.call( new RemoteCallback<Boolean>() {
            @Override
            public void callback( final Boolean result ) {
                final boolean isUserManagerAvailable = Boolean.TRUE.equals( result );
                view.setUserManagerAvailable( isUserManagerAvailable );
                if ( isUserManagerAvailable ) {
                    init();
                }
            }
        } ).isUserManagerInstalled();
    }

    private void init() {
        userManagementService.call( new RemoteCallback<List<UserInformation>>() {
                                        @Override
                                        public void callback( final List<UserInformation> userInformation ) {
                                            view.setContent( userInformation,
                                                             isReadOnly );
                                        }
                                    },
                                    new ErrorCallback<Message>() {
                                        @Override
                                        public boolean error( final Message message,
                                                              final Throwable throwable ) {
                                            ErrorPopup.showMessage( UserManagementConstants.INSTANCE.genericError() + "\n" + throwable.getMessage() );

                                            return false;
                                        }
                                    }
                                  ).getUsers();
    }

    @WorkbenchPartTitle
    public String getTitle() {
        return UserManagementConstants.INSTANCE.userManagementTitle();
    }

    @WorkbenchPartView
    public UberView<UserManagementPresenter> getView() {
        return view;
    }

    public void addUser() {
        addUserPopup.setCallbackCommand( new Command() {

            @Override
            public void execute() {
                final String userName = addUserPopup.getUserName();
                final String userPassword = addUserPopup.getUserPassword();
                final String userRoles = addUserPopup.getUserRoles();
                userManagementService.call( new RemoteCallback<Void>() {
                                                @Override
                                                public void callback( final Void o ) {
                                                    //Do nothing
                                                }
                                            },
                                            new ErrorCallback<Message>() {
                                                @Override
                                                public boolean error( final Message message,
                                                                      final Throwable throwable ) {
                                                    ErrorPopup.showMessage( UserManagementConstants.INSTANCE.genericError() + "\n" + throwable.getMessage() );

                                                    return false;
                                                }
                                            }
                                          ).addUser( new UserInformationWithPassword( userName,
                                                                                      userPassword,
                                                                                      UserManagementUtils.convertUserRoles( userRoles ) ) );
            }
        } );
        addUserPopup.show();
    }

    public void deleteUser( final UserInformation userInformation ) {
        userManagementService.call( new RemoteCallback<List<UserInformation>>() {
                                        @Override
                                        public void callback( final List<UserInformation> userInformation ) {
                                            view.setContent( userInformation,
                                                             isReadOnly );
                                        }
                                    },
                                    new ErrorCallback<Message>() {
                                        @Override
                                        public boolean error( final Message message,
                                                              final Throwable throwable ) {
                                            ErrorPopup.showMessage( UserManagementConstants.INSTANCE.genericError() + "\n" + throwable.getMessage() );

                                            return false;
                                        }
                                    }
                                  ).deleteUser( userInformation );
    }

    public void editUser( final UserInformation userInformation ) {
        editUserPopup.setCallbackCommand( new Command() {

            @Override
            public void execute() {
                final String userName = userInformation.getUserName();
                final String userPassword = editUserPopup.getUserPassword();
                final boolean isUserPasswordChanged = editUserPopup.isPasswordChanged();
                final String userRoles = editUserPopup.getUserRoles();

                if ( isUserPasswordChanged ) {
                    updateUser( userName,
                                userPassword,
                                userRoles );
                } else {
                    updateUser( userName,
                                userRoles );
                }
            }

            private void updateUser( final String userName,
                                     final String userRoles ) {
                userManagementService.call( new RemoteCallback<Void>() {
                                                @Override
                                                public void callback( final Void o ) {
                                                    //Do nothing
                                                }
                                            },
                                            new ErrorCallback<Message>() {
                                                @Override
                                                public boolean error( final Message message,
                                                                      final Throwable throwable ) {
                                                    ErrorPopup.showMessage( UserManagementConstants.INSTANCE.genericError() + "\n" + throwable.getMessage() );

                                                    return false;
                                                }
                                            }
                                          ).updateUser( new UserInformation( userName,
                                                                             UserManagementUtils.convertUserRoles( userRoles ) ) );
            }

            private void updateUser( final String userName,
                                     final String userPassword,
                                     final String userRoles ) {
                userManagementService.call( new RemoteCallback<Void>() {
                                                @Override
                                                public void callback( final Void o ) {
                                                    //Do nothing
                                                }
                                            },
                                            new ErrorCallback<Message>() {
                                                @Override
                                                public boolean error( final Message message,
                                                                      final Throwable throwable ) {
                                                    ErrorPopup.showMessage( UserManagementConstants.INSTANCE.genericError() + "\n" + throwable.getMessage() );

                                                    return false;
                                                }
                                            }
                                          ).updateUser( new UserInformationWithPassword( userName,
                                                                                         userPassword,
                                                                                         UserManagementUtils.convertUserRoles( userRoles ) ) );
            }

        } );
        editUserPopup.setUserInformation( userInformation );
        editUserPopup.show();

    }

}
