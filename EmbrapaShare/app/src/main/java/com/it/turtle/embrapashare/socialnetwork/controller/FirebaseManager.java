package com.it.turtle.embrapashare.socialnetwork.controller;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {

    private final String NODE_PATH;
    private DatabaseReference mDatabaseNode;

    private OnFirebaseAddResponseListener addResponseListener;
    private OnDataChangedListener dataChangedListener;

    private List<Object> childrenList;

    private Class targetClass;

    public FirebaseManager(String nodePath, Class targetClass) {
        NODE_PATH = nodePath;
        mDatabaseNode = FirebaseDatabase.getInstance().getReference(NODE_PATH);

        this.targetClass = targetClass;

        childrenList = new ArrayList<>();
    }

    public void setOnFirebaseAddResponseListener (OnFirebaseAddResponseListener listener) {
        addResponseListener = listener;
    }

    public void setOnDataChangedListener (OnDataChangedListener listener) {
        dataChangedListener = listener;

        mDatabaseNode.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                handleChildAdded(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                handleChildRemoved(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addToNode (Object object) {
        mDatabaseNode.push().setValue(object, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (addResponseListener != null) {
                    addResponseListener.onResponse(databaseError, databaseReference);
                }
            }
        });
    }

    public void setChildValue (String child, Object data) {
        mDatabaseNode.child(child).setValue(data);
    }

    public List<Object> getChildrenList() {
        return childrenList;
    }

    private void handleChildAdded(DataSnapshot dataSnapshot) {
        Log.d("Vinicius", "********************************** handleChildAdded");
        childrenList.add(dataSnapshot.getValue(targetClass));
        dataChangedListener.onDatabaseUpdate(dataSnapshot);
    }

    private void handleChildRemoved (DataSnapshot dataSnapshot) {
        Log.d("Vinicius", "********************************** handleChildRemoved");
        try {
            Log.d("Vinicius", "handleChildRemoved --> " + dataSnapshot.getValue(targetClass).toString());
            removeElement(dataSnapshot.getValue(targetClass));
            dataChangedListener.onDatabaseUpdate(dataSnapshot);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnDataChangedListener {
        void onDatabaseUpdate(DataSnapshot dataSnapshot);
    }

    public interface OnFirebaseAddResponseListener {
        void onResponse(DatabaseError databaseError, DatabaseReference databaseReference);
//        Sugestão do que pode ser implementado
//        if (databaseError != null) {
//            System.out.println("Data could not be saved " + databaseError.getMessage());
//            String postId = pushedPostRef.getKey();
//        } else {
//            System.out.println("Data saved successfully.");
//        }
    }

    private void removeElement (Object object) {
        //TODO arrumar a remoção
        for (Object o : childrenList) {
            if(object.equals(o)) {
                childrenList.remove(o);
                return;
            }
        }
    }
}
