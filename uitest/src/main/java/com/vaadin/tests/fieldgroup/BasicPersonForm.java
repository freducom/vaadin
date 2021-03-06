package com.vaadin.tests.fieldgroup;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.VaadinRequest;
import com.vaadin.tests.components.AbstractTestUIWithLog;
import com.vaadin.tests.data.bean.Address;
import com.vaadin.tests.data.bean.Country;
import com.vaadin.tests.data.bean.Person;
import com.vaadin.tests.data.bean.Sex;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.v7.data.util.converter.LegacyStringToBooleanConverter;
import com.vaadin.v7.data.validator.LegacyEmailValidator;
import com.vaadin.v7.data.validator.LegacyIntegerRangeValidator;
import com.vaadin.v7.data.validator.LegacyStringLengthValidator;
import com.vaadin.v7.ui.LegacyTextField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class BasicPersonForm extends AbstractTestUIWithLog {

    private LegacyTextField firstName;
    private TextArea lastName;
    private LegacyTextField email;
    private LegacyTextField age;
    private Table sex;
    private LegacyTextField deceased;

    public class Configuration {
        public boolean preCommitFails = false;
        public boolean postCommitFails = false;

        public boolean isPreCommitFails() {
            return preCommitFails;
        }

        public void setPreCommitFails(boolean preCommitFails) {
            this.preCommitFails = preCommitFails;
        }

        public boolean isPostCommitFails() {
            return postCommitFails;
        }

        public void setPostCommitFails(boolean postCommitFails) {
            this.postCommitFails = postCommitFails;
        }

    }

    private Configuration configuration = new Configuration();

    private class ConfigurationPanel extends Panel {
        private CheckBox preCommitCheckBox = new CheckBox("Pre Commit Fails",
                configuration.isPreCommitFails());
        private CheckBox postCommitCheckBox = new CheckBox("Post Commit Fails",
                configuration.isPostCommitFails());

        public ConfigurationPanel() {
            super("Configuration", new VerticalLayout());
            ((VerticalLayout) getContent()).setMargin(true);
            preCommitCheckBox.addValueChangeListener(
                    event -> configuration.setPreCommitFails(event.getValue()));
            postCommitCheckBox.addValueChangeListener(event -> configuration
                    .setPostCommitFails(event.getValue()));

            ((ComponentContainer) getContent()).addComponents(preCommitCheckBox,
                    postCommitCheckBox);
        }
    }

    @Override
    protected void setup(VaadinRequest request) {
        addComponent(log);
        Panel confPanel = new ConfigurationPanel();
        addComponent(confPanel);

        final FieldGroup fieldGroup = new BeanFieldGroup<Person>(Person.class);
        fieldGroup.addCommitHandler(new CommitHandler() {

            @Override
            public void preCommit(CommitEvent commitEvent)
                    throws CommitException {
                if (configuration.preCommitFails) {
                    throw new CommitException(
                            "Error in preCommit because first name is "
                                    + getPerson(commitEvent.getFieldBinder())
                                            .getFirstName());
                }

            }

            @Override
            public void postCommit(CommitEvent commitEvent)
                    throws CommitException {
                if (configuration.postCommitFails) {
                    throw new CommitException(
                            "Error in postCommit because first name is "
                                    + getPerson(commitEvent.getFieldBinder())
                                            .getFirstName());
                }
            }
        });

        fieldGroup.setBuffered(true);

        fieldGroup.buildAndBindMemberFields(this);
        addComponent(firstName);
        addComponent(lastName);
        addComponent(email);
        addComponent(age);
        addComponent(sex);
        addComponent(deceased);

        Button commitButton = new Button("Commit", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                String msg = "Commit succesful";
                try {
                    fieldGroup.commit();
                } catch (CommitException e) {
                    msg = "Commit failed: " + e.getMessage();
                }
                Notification notification = new Notification(msg);
                notification.setDelayMsec(Notification.DELAY_FOREVER);
                notification.show(getPage());
                log(msg);

            }
        });
        Button discardButton = new Button("Discard",
                new Button.ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        fieldGroup.discard();
                        log("Discarded changes");

                    }
                });
        Button showBean = new Button("Show bean values",
                new Button.ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        log(getPerson(fieldGroup).toString());

                    }
                });
        addComponent(commitButton);
        addComponent(discardButton);
        addComponent(showBean);
        email.addValidator(new LegacyEmailValidator("Must be a valid address"));
        lastName.addValidator(new LegacyStringLengthValidator(
                "Must be min 5 chars", 5, null, true));

        age.addValidator(new LegacyIntegerRangeValidator(
                "Must be between 0 and 150, {0} is not", 0, 150));
        sex.setPageLength(0);
        deceased.setConverter(new LegacyStringToBooleanConverter() {
            @Override
            protected String getTrueString() {
                return "YAY!";
            }

            @Override
            protected String getFalseString() {
                return "NAAAAAH";
            }
        });
        Person p = new Person("John", "Doe", "john@doe.com", 64, Sex.MALE,
                new Address("John street", 11223, "John's town", Country.USA));
        fieldGroup.setItemDataSource(new BeanItem<Person>(p));
    }

    @SuppressWarnings("unchecked")
    public static Person getPerson(FieldGroup binder) {
        return ((BeanItem<Person>) binder.getItemDataSource()).getBean();
    }

    @Override
    public String getDescription() {
        return "Basic Person Form";
    }

    @Override
    protected Integer getTicketNumber() {
        return 8094;
    }

}
