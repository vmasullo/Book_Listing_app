package com.example.android.booklisting;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Enzo on the 14/07/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    private final static String AUTORE = "Autore: ";
    private final static String EDITORE = "Editore: ";
    private final static String PAGINE = "Pagine: ";

    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    private static class ViewHolder {
        ImageView thumbnail;
        TextView titolo;
        TextView autore;
        TextView editore;
        TextView pagine;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        View bookList = convertView;
        if (bookList == null) {
            bookList = LayoutInflater.from(getContext()).inflate(R.layout.book_listing_sample, parent, false);
            holder = new ViewHolder();
            holder.thumbnail = (ImageView) bookList.findViewById(R.id.thumbnail);
            holder.titolo = (TextView) bookList.findViewById(R.id.book_title);
            holder.autore = (TextView) bookList.findViewById(R.id.autore);
            holder.editore = (TextView) bookList.findViewById(R.id.editore);
            holder.pagine = (TextView) bookList.findViewById(R.id.pagine);
            bookList.setTag(holder);
        } else {
            holder = (ViewHolder) bookList.getTag();
        }
        Book currentBook = getItem(position);

        Picasso.with(getContext()).load(currentBook.getThumbnail()).into(holder.thumbnail);

        holder.titolo.setText(currentBook.getTitle());

        holder.autore.setText(AUTORE + currentBook.getAutore());

        holder.editore.setText(EDITORE + currentBook.getEditore());

        holder.pagine.setText(PAGINE + currentBook.getPagine());

        return bookList;
    }

}
