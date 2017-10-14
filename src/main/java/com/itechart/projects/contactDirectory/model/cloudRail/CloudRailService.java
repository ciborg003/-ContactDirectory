package com.itechart.projects.contactDirectory.model.cloudRail;

import com.cloudrail.si.CloudRail;
import com.cloudrail.si.interfaces.CloudStorage;
import com.cloudrail.si.servicecode.commands.awaitCodeRedirect.LocalReceiver;
import com.cloudrail.si.services.Dropbox;

public class CloudRailService {

    private static String licenseKey = "59e0973da5a11670ad739bad";
    private static String key = "cdhnvulp856ahth";
    private static String secret = "217dz0hr6ipjf3z";

    public void main() {
        CloudRail.setAppKey(licenseKey);

        CloudStorage service = null;

        Dropbox dropbox = new Dropbox(new LocalReceiver(8080),
                key,
                secret,
                "http://localhost:8080/ServletController/app&action=auth",
                "Dropbox");

        service = dropbox;
        service.login();
    }
}
