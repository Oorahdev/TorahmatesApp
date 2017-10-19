package com.vb.torahmate.events;

import com.vb.torahmate.models.ContactTorahmateModel;

import java.util.List;

/**
 * Created by Najia on 7/24/2015.
 */
public class ContactTorahmateEvent extends Event {

    private final List<ContactTorahmateModel> contactTorahmates;

    public ContactTorahmateEvent(List<ContactTorahmateModel> contactTorahmateList) {
        this.contactTorahmates = contactTorahmateList;
    }

    public List<ContactTorahmateModel> getList() {
        return contactTorahmates;
    }
}
