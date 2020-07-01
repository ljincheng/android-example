package cn.booktable.note.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.booktable.note.viewmodel.converter.DateConverter;
import cn.booktable.note.viewmodel.notices.NoticesDao;
import cn.booktable.note.viewmodel.notices.NoticesDetail;
import cn.booktable.note.viewmodel.user.User;
import cn.booktable.note.viewmodel.user.UserDao;

@Database(entities = {User.class, NoticesDetail.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class SystemRoomDatabase extends RoomDatabase {


    public abstract UserDao userDao();
    public abstract NoticesDao noticesDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile SystemRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static SystemRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SystemRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SystemRoomDatabase.class, "system_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
//            databaseWriteExecutor.execute(() -> {
//                // Populate the database in the background.
//                // If you want to start with more words, just add them.
//
////                UserDao dao = INSTANCE.userDao();
////                dao.deleteAll();
////
////                User user = new User();
////                user.setId(1);
////                user.setUserName("普京");
////                user.setGender("男");
////                user.setIcon("");
////                user.setIsTop(1);
////                dao.insert(user);
////                user = new User();
////                user.setId(2);
////                user.setUserName("特朗普");
////                user.setGender("男");
////                user.setIcon("");
////                user.setIsTop(0);
////                dao.insert(user);
//            });
        }
    };
}
