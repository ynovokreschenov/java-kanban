package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private static class Node{
        Task item;
        Node next;
        Node prev;

        Node(Node prev, Task element, Node next){
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    HashMap<Integer, Node> history;
    Node first;
    Node last;

    public InMemoryHistoryManager() {
        this.history = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        Node node = history.get(task.getId());
        if (node != null) {
            removeNode(node);
        }
        linkLast(task);
    }

    @Override
    public void remove(int id){
        Node node = history.get(id);
        if (node != null) {
            removeNode(node);
        }
    }

    @Override
    public List<Task> getTasks() {
        ArrayList<Task> list = new ArrayList<>();
        Node current = first;
        while (current != null){
            list.add(current.item);
            current = current.next;
        }
        return list;
    }

    private void linkLast(Task task){
        final Node lastNode = last;
        final Node newNode = new Node(lastNode, task, null);
        last = newNode;
        if (lastNode == null){
            first = newNode;
        } else {
            lastNode.next = newNode;
        }
        history.put(task.getId(), newNode);
    }
    private void removeNode(Node node){
        int index = node.item.getId();
        if (history.containsKey(index)) {
            Node nodePrev = node.prev;
            Node nodeNext = node.next;
            if (nodePrev == null){
                // если попали на первую ноду
                if (nodeNext != null) {
                    nodeNext.prev = null;
                    first = nodeNext;
                } else {
                    first = null;
                }
            } else if (nodeNext == null) {
                // если попали на последнюю ноду
                if (nodePrev != null) {
                    nodePrev.next = null;
                    last = nodePrev;
                } else {
                    last = null;
                }
            } else {
                // если попали в середину
                nodePrev.next = nodeNext.prev;
            }
            history.remove(index);
        }
    }
}
