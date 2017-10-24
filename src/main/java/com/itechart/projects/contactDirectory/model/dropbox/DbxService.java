package com.itechart.projects.contactDirectory.model.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbxService {

    private DbxUser user;
    private DbxClientV2 client;
    private FullAccount account;

    public DbxService(DbxUser user) throws DbxException {
        this.user = user;
        authClient();
    }

    private void authClient() throws DbxException {
        DbxRequestConfig config = new DbxRequestConfig(user.getUsername());
        client = new DbxClientV2(config, user.getAccessToken());
        account = client.users().getCurrentAccount();
    }

    public DbxService() {
    }

    public DbxUser getUser() {
        return user;
    }

    public void setUser(DbxUser user) {
        this.user = user;
    }

    public void uploadFile(InputStream file, String fileName) {
        try {
            FileMetadata metadata = client.files().uploadBuilder(fileName).uploadAndFinish(file);
        } catch (DbxException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteFile(String path) {
        try {
            Metadata metadata = client.files().delete(path);
        } catch (DbxException ex) {
            ex.printStackTrace();
        }
    }

    public void readFile(String folderName, OutputStream stream) {
        System.out.println(folderName);
        try {
            FileMetadata metadata = client.files().downloadBuilder(folderName).download(stream);

        } catch (NullPointerException ex) {
            System.out.println(client == null);
            ex.printStackTrace();
        } catch (DbxException | IOException ex) {
            Logger.getLogger(DbxService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public boolean isExists(String path) {
        try {
            FileMetadata metadata = client.files().downloadBuilder(path).download(null);
        } catch (NullPointerException ex) {
        } catch (DbxException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        return true;
    }
    
    public long getSize(String path){
        try {
            return client.files().downloadBuilder(path).start().getResult().getSize();
        } catch (DbxException ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    public DbxClientV2 getClient() {
        return client;
    }

    public void setClient(DbxClientV2 client) {
        this.client = client;
    }

    public FullAccount getAccount() {
        return account;
    }

    public void setAccount(FullAccount account) {
        this.account = account;
    }
    
    
}