package com.example.yallah_project.dtos;

import com.example.yallah_project.model.GovernmentIdType;

public class OrganisateurSwitchRequest {
    private GovernmentIdType governmentIdType;
    private byte[] governmentIdImage;

    public GovernmentIdType getGovernmentIdType() {
        return governmentIdType;
    }

    public void setGovernmentIdType(GovernmentIdType governmentIdType) {
        this.governmentIdType = governmentIdType;
    }

    public byte[] getGovernmentIdImage() {
        return governmentIdImage;
    }

    public void setGovernmentIdImage(byte[] governmentIdImage) {
        this.governmentIdImage = governmentIdImage;
    }
}
