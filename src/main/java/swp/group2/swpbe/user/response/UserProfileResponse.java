package swp.group2.swpbe.user.response;

import swp.group2.swpbe.user.entities.User;

public class UserProfileResponse extends User {
    private int legitMark;

    public UserProfileResponse(String fullName, String email, String password, String avatarUrl, int isVerifyEmail,
            String sid, int legitMark) {
        super(fullName, email, password, avatarUrl, isVerifyEmail, sid);
        this.legitMark = legitMark;
    }

    public UserProfileResponse() {
    }

    public int getLegitMark() {
        return this.legitMark;
    }

    public void setLegitMark(int legitMark) {
        this.legitMark = legitMark;
    }

}
