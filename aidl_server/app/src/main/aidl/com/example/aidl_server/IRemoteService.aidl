// IRemoteService.aidl
package com.example.aidl_server;
import com.example.aidl_server.ContactBO;

// Declare any non-default types here with import statements

interface IRemoteService {
    List<ContactBO> transfer();

    void addContact(in ContactBO contact);
}
