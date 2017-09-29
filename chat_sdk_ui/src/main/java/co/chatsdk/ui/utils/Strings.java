package co.chatsdk.ui.utils;

import android.support.annotation.StringRes;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.chatsdk.core.NM;
import co.chatsdk.core.dao.Message;
import co.chatsdk.core.dao.Thread;
import co.chatsdk.core.dao.User;
import co.chatsdk.core.interfaces.ThreadType;
import co.chatsdk.core.utils.AppContext;
import co.chatsdk.ui.R;

/**
 * Created by benjaminsmiley-andrews on 07/06/2017.
 */

public class Strings {

    public static String t (@StringRes int resId) {
        return AppContext.shared().context().getString(resId);
    }

    public static String payloadAsString (Message message) {
            if (message.getType() != null) {
                switch (message.getMessageType()) {
                    case Text:
                    case System:
                        return message.getTextString();
                    case Image:
                        return t(R.string.image_message);
                    case Location:
                        return t(R.string.location_message);
                    case Audio:
                        return t(R.string.audio_message);
                    case Video:
                        return t(R.string.video_message);
                    case Sticker:
                        return t(R.string.sticker_message);
                    case File:
                        return t(R.string.file_message);
                }
            }
            return t(R.string.unknown_message);
    }

    public static String dateTime (Date date) {
        return new SimpleDateFormat("HH:mm dd/MM/yy").format(date);
    }

    public static String date (Date date) {
        return new SimpleDateFormat("dd/MM/yy").format(date);
    }

    public static String nameForThread (Thread thread) {

        if (StringUtils.isNotEmpty(thread.getName()))
            return thread.getName();

        // Due to the bundle printing when the app run on debug this sometime is null.
        User currentUser = NM.currentUser();
        String name = "";

        if (thread.typeIs(ThreadType.Private)) {

            for (User user : thread.getUsers()){
                if (!user.getId().equals(currentUser.getId())) {
                    String n = user.getName();

                    if (StringUtils.isNotEmpty(n)) {
                        name += (!name.equals("") ? ", " : "") + n;
                    }
                }
            }
        }

        if(name.length() == 0) {
            name = Strings.t(R.string.not_thread);
        }
        return name;
    }

}