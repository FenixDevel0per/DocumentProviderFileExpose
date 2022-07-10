package br.com.fenixdev.documentProviderFileExpose.documentProvider;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract.*;
import android.provider.DocumentsProvider;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileNotFoundException;

import androidx.annotation.Nullable;
import br.com.fenixdev.documentProviderFileExpose.R;
import br.com.fenixdev.documentProviderFileExpose.MainApplication;

public class DocumentProviderExample extends DocumentsProvider {

    private static final String[] DEFAULT_ROOT_PROJECTION =
            new String[]{Root.COLUMN_ROOT_ID, Root.COLUMN_MIME_TYPES,
                    Root.COLUMN_FLAGS, Root.COLUMN_ICON, Root.COLUMN_TITLE,
                    Root.COLUMN_SUMMARY, Root.COLUMN_DOCUMENT_ID,
                    Root.COLUMN_AVAILABLE_BYTES,};
    private static final String[] DEFAULT_DOCUMENT_PROJECTION = new
            String[]{Document.COLUMN_DOCUMENT_ID, Document.COLUMN_MIME_TYPE,
            Document.COLUMN_DISPLAY_NAME, Document.COLUMN_LAST_MODIFIED,
            Document.COLUMN_FLAGS, Document.COLUMN_SIZE,};
    public static String ROOT = MainApplication.getAppContext().getFilesDir().getAbsolutePath();
    private File mBaseDir;

    @Override
    public boolean onCreate() {
        mBaseDir = getContext().getFilesDir();
        return true;
    }

    @Override
    public Cursor queryRoots(String[] projection) throws FileNotFoundException {

        final MatrixCursor result =
                new MatrixCursor(projection != null ? projection : DEFAULT_ROOT_PROJECTION);

        final MatrixCursor.RowBuilder row = result.newRow();
        row.add(Root.COLUMN_ROOT_ID, ROOT);
        row.add(Root.COLUMN_FLAGS, Root.FLAG_SUPPORTS_CREATE |
                Root.FLAG_SUPPORTS_RECENTS |
                Root.FLAG_SUPPORTS_SEARCH);
        row.add(Root.COLUMN_TITLE, getContext().getString(R.string.document_provider_title));
        row.add(Root.COLUMN_DOCUMENT_ID, ROOT);
        row.add(Root.COLUMN_ICON, R.mipmap.ic_document_provider);

        return result;    }

    @Override
    public Cursor queryDocument(String documentId, String[] projection) throws FileNotFoundException {
        final MatrixCursor result =
                new MatrixCursor(projection != null ? projection : DEFAULT_DOCUMENT_PROJECTION);
        final File parent = new File(documentId);
        final MatrixCursor.RowBuilder row = result.newRow();
        row.add(Document.COLUMN_DOCUMENT_ID, documentId);
        row.add(Document.COLUMN_DISPLAY_NAME, parent.getName());
        row.add(Document.COLUMN_FLAGS, 0);
        row.add(Document.COLUMN_MIME_TYPE, getTypeForFile(parent));
        row.add(Document.COLUMN_SIZE, null);
        row.add(Document.COLUMN_LAST_MODIFIED, null);

        return result;
    }

    @Override
    public Cursor queryChildDocuments(String parentDocumentId, String[] projection, String s1) throws FileNotFoundException {
        final MatrixCursor result =
                new MatrixCursor(projection != null ? projection : DEFAULT_DOCUMENT_PROJECTION);
        final File parent = new File(parentDocumentId);
        for (File file : parent.listFiles()) {
            final MatrixCursor.RowBuilder row = result.newRow();
            /** I'm using the absoluth path as Id just to make easier. You should use something else as Id.
             * Just remember to know exacly how retrieve the real Document from your custom Id
             */
            row.add(Document.COLUMN_DOCUMENT_ID, file.getAbsolutePath());
            row.add(Document.COLUMN_DISPLAY_NAME, file.getName());
            row.add(Document.COLUMN_FLAGS, 0);
            row.add(Document.COLUMN_MIME_TYPE, getTypeForFile(file));
            row.add(Document.COLUMN_SIZE, null);
            row.add(Document.COLUMN_LAST_MODIFIED, null);
        }
        return result;
    }

    @Override
    public ParcelFileDescriptor openDocument(String documentId, String mode, @Nullable CancellationSignal signal) throws FileNotFoundException {

        final File file = new File(documentId);
        final int accessMode = ParcelFileDescriptor.parseMode(mode);

        return ParcelFileDescriptor.open(file, accessMode);
    }

    private static String getTypeForFile(File file) {
        if (file.isDirectory()) {
            return Document.MIME_TYPE_DIR;
        } else {
            return getTypeForName(file.getName());
        }
    }

    private static String getTypeForName(String name){
        String mimeType = "application/octet-stream";
        int lastDot = name.lastIndexOf(".");
        if(lastDot >= 0) {
            String extension = name.substring(lastDot + 1);
            String mimeAux = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            if(mimeAux != null){
                mimeType = mimeAux;
            }
        }
        return mimeType;
    }



}
