package com.phongbm.message;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ProgressCallback;
import com.phongbm.ahihi.R;
import com.phongbm.common.GlobalApplication;
import com.phongbm.libs.TriangleShapeView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends BaseAdapter implements View.OnClickListener {
    public static final String TAG = "MessageAdapter";

    public static final int TYPE_COUNT = 2;
    public static final int TYPE_OUTGOING = 0;
    public static final int TYPE_INCOMING = 1;

    private GlobalApplication globalApplication;
    private ArrayList<MessageItem> messageItems;
    private LayoutInflater layoutInflater;
    private Bitmap outGoingMessageAvatar, inComingMessageAvatar;

    public MessageAdapter(Context context, String inComingMessageId) {
        layoutInflater = LayoutInflater.from(context);
        this.messageItems = new ArrayList<>();
        globalApplication = (GlobalApplication) context.getApplicationContext();
        outGoingMessageAvatar = globalApplication.getAvatar();
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public MessageItem getItem(int position) {
        return messageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return messageItems.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        int typePre = (position > 0) ? getItemViewType(position - 1) : 0;
        ViewHolder viewHolder;
        if (convertView == null) {
            switch (type) {
                case TYPE_OUTGOING:
                    convertView = layoutInflater.inflate(R.layout.item_message_outgoing, parent, false);
                    break;
                case TYPE_INCOMING:
                    convertView = layoutInflater.inflate(R.layout.item_message_incoming, parent, false);
                    break;
            }
            viewHolder = new ViewHolder();
            viewHolder.space = convertView.findViewById(R.id.space);
            viewHolder.imgAvatar = (CircleImageView) convertView.findViewById(R.id.imgAvatar);
            viewHolder.imgTriangel = (TriangleShapeView) convertView.findViewById(R.id.imgTriangel);
            viewHolder.txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        switch (type) {
            case TYPE_OUTGOING:
                viewHolder.imgAvatar.setImageBitmap(outGoingMessageAvatar);
                viewHolder.imgTriangel.setBackgroundColor(Color.parseColor("#4caf50"));
                break;
            case TYPE_INCOMING:
                viewHolder.imgAvatar.setImageResource(R.drawable.ic_ava_2);
                viewHolder.imgTriangel.setBackgroundColor(Color.parseColor("#eeeeee"));
                break;
        }
        if (position == 0 && messageItems.get(0).getMode() == 1) {
            viewHolder.imgTriangel.setBackgroundColor(Color.parseColor("#00000000"));
        }
        if (position > 0 && (type == TYPE_OUTGOING && typePre == TYPE_OUTGOING)
                || (type == TYPE_INCOMING && typePre == TYPE_INCOMING)) {
            if (messageItems.get(position).getMode() == 1 && messageItems.get(position - 1).getMode() == 0
                    || messageItems.get(position).getMode() == 0 && messageItems.get(position - 1).getMode() == 0
                    || messageItems.get(position).getMode() == 1 && messageItems.get(position - 1).getMode() == 1) {
                viewHolder.imgAvatar.setImageResource(R.drawable.ic_transparent);
                viewHolder.imgTriangel.setBackgroundColor(Color.parseColor("#00000000"));
            }
        }
        if ((type == TYPE_OUTGOING && typePre == TYPE_INCOMING)
                || (type == TYPE_INCOMING && typePre == TYPE_OUTGOING)) {
            viewHolder.space.setVisibility(View.VISIBLE);
            if (messageItems.get(position).getMode() == 1) {
                viewHolder.imgTriangel.setBackgroundColor(Color.parseColor("#00000000"));
            }
        } else {
            viewHolder.space.setVisibility(View.GONE);
        }

        switch (messageItems.get(position).getMode()) {
            case 0:
            case 1:
                viewHolder.txtMessage.setText(messageItems.get(position).getContent());
                viewHolder.txtMessage.setOnClickListener(null);
                viewHolder.txtMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                break;
            case 2:
                viewHolder.txtMessage.setOnClickListener(this);
                String content = messageItems.get(position).getContent().toString();
                Log.i(TAG, content);
                String objectId = content.substring(0, content.lastIndexOf("/"));
                String fileName = content.substring(content.lastIndexOf("/") + 1);
                viewHolder.txtMessage.setText(Html.fromHtml(
                        "<u><font color='#827ca3'>" + fileName + "</font></u>   "));
                viewHolder.txtMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                        R.drawable.ic_message_download, 0);
                viewHolder.txtMessage.setTag(objectId);
                break;
        }

        if (messageItems.get(position).getMode() == 1) {
            viewHolder.txtMessage.setBackgroundColor(Color.parseColor("#00000000"));
        } else {
            switch (type) {
                case TYPE_OUTGOING:
                    viewHolder.txtMessage.setBackgroundResource(R.drawable.bg_message_outgoing);
                    break;
                case TYPE_INCOMING:
                    viewHolder.txtMessage.setBackgroundResource(R.drawable.bg_message_incoming);
                    break;
            }
        }
        return convertView;
    }

    @Override
    public void onClick(final View view) {
        final AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
        alertDialog.setTitle("Confirm");
        alertDialog.setMessage("Download");
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "DOWNLOAD",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String objectId = view.getTag().toString();
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
                        query.getInBackground(objectId, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject parseObject, ParseException e) {
                                if (e != null) {
                                    return;
                                }
                                final ParseFile parseFile = parseObject.getParseFile("file");
                                Log.i(TAG, parseFile.getName());
                                Log.i(TAG, parseFile.getUrl());
                                String fileName = parseFile.getName().substring(
                                        parseFile.getName().lastIndexOf("-") + 1);
                                Log.i(TAG, fileName);
                                final String path = Environment.getExternalStorageDirectory().getPath() +
                                        "/" + Environment.DIRECTORY_DOWNLOADS + "/" + fileName;
                                Log.i(TAG, path);
                                parseFile.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] bytes, ParseException e) {
                                        if (e != null) {
                                            Toast.makeText(view.getContext(), "Download fail: "
                                                    + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        File file = new File(path);
                                        Log.i(TAG, "new File");
                                        boolean checkCreateFile = false;
                                        int i = 1;
                                        while (!checkCreateFile) {
                                            if (!file.exists()) try {
                                                Log.i(TAG, "!file.exists()");
                                                file.createNewFile();
                                                checkCreateFile = true;
                                                FileOutputStream outputStream = new FileOutputStream(file);
                                                outputStream.write(bytes);
                                                outputStream.close();
                                                Toast.makeText(view.getContext(), "Download successfully",
                                                        Toast.LENGTH_SHORT).show();
                                            } catch (IOException iOE) {
                                                iOE.printStackTrace();
                                            }
                                            else {
                                                int index = path.lastIndexOf(".");
                                                String newPath = path.replace(path.substring(index,
                                                        index + 1), "(" + i + ").");
                                                file = new File(newPath);
                                                i++;
                                            }
                                        }
                                    }
                                }, new ProgressCallback() {
                                    @Override
                                    public void done(Integer progress) {
                                        Log.i(TAG, "percent: " + progress);
                                        // if (progress > 50) parseFile.cancel();
                                    }
                                });
                            }
                        });
                        alertDialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private class ViewHolder {
        View space;
        TriangleShapeView imgTriangel;
        CircleImageView imgAvatar;
        TextView txtMessage;
    }

    public void addMessage(int position, MessageItem messageItem) {
        messageItems.add(position, messageItem);
        this.notifyDataSetChanged();
    }

}