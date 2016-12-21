package com.chatapp.mvp.onboarding;

/**
 * Created by thanhnguyen on 12/22/16.
 */

public class OnboardingItem {
    private String title;
    private String description;

    public OnboardingItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
