package apputvikling.jorber.s354410_plans.db;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    private final Context mCtx;
    private static DatabaseClient mInstance;
    private final AppDatabase mAppDatabase;

    private DatabaseClient(Context ctx) {
        this.mCtx = ctx;
        this.mAppDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "Contact").fallbackToDestructiveMigration().build();
    }

    public static synchronized DatabaseClient getInstance(Context ctx) {
        if(mInstance == null) {
            mInstance = new DatabaseClient(ctx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return mAppDatabase;
    }
}
