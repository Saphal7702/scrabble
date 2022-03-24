/*
A trie implementation to store and search the dictionary.
 */
public class Trie {
    private final Node root;
    public Trie(){
        root = new Node('$');
    }
    public void insert(String word){
        Node current = root;
        for(int i=0; i< word.length();i++){
            char c= word.charAt(i);
            if(current.children[c-'a']==null) current.children[c-'a']= new Node(c);
            current=current.children[c-'a'];
        }
        current.isWord=true;
    }

    public boolean search(String word){
        Node node = getNode(word);
        return node!=null && node.isWord;
    }

    public boolean startsWith(String prefix){
        return getNode(prefix)!=null;
    }

    public Node getNode(String word){
        Node current = root;
        for(int i=0; i< word.length();i++){
            char c= word.charAt(i);
            if(current.children[c-'a']==null) return null;
            current=current.children[c-'a'];
        }
        return current;
    }
}
