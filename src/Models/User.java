package Models;

public abstract class User {
    
    private int _id;
    private String _userName;
    public User(int _id, String _userName) {
        this._id = _id;
        this._userName = _userName;
    }
    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public String get_userName() {
        return _userName;
    }
    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    
}
