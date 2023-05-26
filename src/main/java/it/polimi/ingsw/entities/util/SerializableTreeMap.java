package it.polimi.ingsw.entities.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * Class to implement personalized comparator and make TreeMaps Serializable (send&receive scoreboard)
 * @param <K> Key
 * @param <V> Value
 */
public class SerializableTreeMap<K, V> extends TreeMap<K, V> implements Serializable {
    public SerializableTreeMap(Comparator<K> comparator) {
    }

    /**
     * Method used to add personalized Comparator
     * @return comparator itself
     */
    @Override
    public Comparator<? super K> comparator() {
        return super.comparator();
    }
}
