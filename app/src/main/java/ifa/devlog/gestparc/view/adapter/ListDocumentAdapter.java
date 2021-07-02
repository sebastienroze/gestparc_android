package ifa.devlog.gestparc.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ifa.devlog.gestparc.R;
import ifa.devlog.gestparc.model.Document;

public class ListDocumentAdapter extends RecyclerView.Adapter
        <RecyclerView.ViewHolder> {

    private EcouteurClicDocument ecouteur;

    public interface EcouteurClicDocument {
        void onClicDocument(Document item);
    }

    private List<Document> listeDocument;

    public ListDocumentAdapter(List<Document> listeDocument, EcouteurClicDocument ecouteur) {
        this.ecouteur = ecouteur;
        this.listeDocument = listeDocument;
    }

    static class DocumentViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutItem;
        TextView textViewNom;
        TextView textViewFichier;
        CardView cardView;
        ImageView imageViewbottomList;

        public DocumentViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layout_item_document);
            textViewNom = itemView.findViewById(R.id.textView_itemDocumentNom);
            textViewFichier = itemView.findViewById(R.id.textView_itemDocumentFichier);
            imageViewbottomList = itemView.findViewById(R.id.imageView_bottomListeDocument);
            cardView = itemView.findViewById(R.id.cardView_document);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_document,parent,false);
        return new DocumentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Document document = listeDocument.get(position);
        DocumentViewHolder documentViewHolder = (DocumentViewHolder) holder;
        documentViewHolder.textViewNom.setText(document.getNom());
        documentViewHolder.textViewFichier.setText(document.getOriginalFilename());
        documentViewHolder.layoutItem.setOnClickListener( v ->
                ecouteur.onClicDocument(document)
        );
        if (position != listeDocument.size() - 1) {
            documentViewHolder.imageViewbottomList.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return listeDocument.size();
    }
}
