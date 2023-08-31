package org.mjjaen.mongo.listener;

import lombok.extern.slf4j.Slf4j;
import org.mjjaen.mongo.businessObject.User;
import org.springframework.data.mongodb.core.mapping.event.*;

@Slf4j
public class EventListener extends AbstractMongoEventListener<User> {
    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        log.info("Logging onBeforeConvert event ...");
        log.info(event.getSource().toString());
    }

    @Override
    public void onBeforeSave(BeforeSaveEvent<User> event) {
        log.info("Logging onBeforeSave event ...");
        log.info(event.getSource().toString());
    }

    @Override
    public void onAfterSave(AfterSaveEvent<User> event) {
        log.info("Logging onAfterSave event ...");
        log.info(event.getSource().toString());
    }

    @Override
    public void onAfterLoad(AfterLoadEvent<User> event) {
        log.info("Logging onAfterLoad event ...");
        log.info(event.getSource().toString());
    }

    @Override
    public void onAfterConvert(AfterConvertEvent<User> event) {
        log.info("Logging onAfterConvert event ...");
        log.info(event.getSource().toString());
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<User> event) {
        log.info("Logging onAfterDelete event ...");
        log.info(event.getSource().toString());
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<User> event) {
        log.info("Logging onBeforeDelete event ...");
        log.info(event.getSource().toString());
    }
}
