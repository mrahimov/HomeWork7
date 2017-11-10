package nyc.c4q.homework7;

import android.os.Parcel;
import android.os.Parcelable;


public class Score implements Parcelable {

    private int value;

    public Score(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.value);
    }

    protected Score(Parcel in) {
        this.value = in.readInt();
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel source) {
            return new Score(source);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };
}