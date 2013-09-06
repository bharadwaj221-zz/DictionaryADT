public class DictionaryFactory {
  private Dictionary dictionary;
  public static void main(String[] args)
  {
    //Raise Exception if args.length == 0 
    String implementation = "BST";
    implementation = args[0];
    DictionaryFactory dictionaryFactory = new DictionaryFactory();
    Dictionary dictionary = dictionaryFactory.getDictionary(implementation);
    //Perform various operations on the dictionary....
  }

  public Dictionary getDictionary(String dictionaryType)
  {
     if(dictionaryType.equals("BST"))
        dictionary = new BST();

     if(dictionaryType.equals("RBT"))
        dictionary = new RBT();

     return dictionary;
  }

}