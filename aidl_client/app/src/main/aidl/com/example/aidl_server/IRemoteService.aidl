// IRemoteService.aidl
package com.example.aidl_server;
import com.example.aidl_server.ContactBO;

interface IRemoteService {
    List<ContactBO> transfer();
    void addContact(in ContactBO contact);
}
