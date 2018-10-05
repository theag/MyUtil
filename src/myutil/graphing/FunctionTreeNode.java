/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutil.graphing;

import java.util.ArrayDeque;
import java.util.ArrayList;
import myutil.MyMath;

/**
 *
 * @author nbp184
 */
public class FunctionTreeNode {
    
    public static FunctionTreeNode makeTree(String function, String variable) {
        FunctionTreeNode root = new FunctionTreeNode(Calculator.whiteSpaceStrip(function).replaceAll("E", Calculator.sn).toLowerCase(), true);
        ArrayDeque<FunctionTreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            int result = queue.peek().split(variable);
            if(result == 0) {
                queue.pop();
            } else if(result == 1) {
                queue.add(queue.pop().getLeftChild());
            } else if(result == 2) {
                queue.add(queue.peek().getLeftChild());
                queue.add(queue.pop().getRightChild());
            }
        }
        return root;
    }
    
    public String tempValue;
    
    private String value;
    private boolean isLeaf;
    private FunctionTreeNode parent;
    private FunctionTreeNode[] children;
    private String totalValue;
    
    public FunctionTreeNode(String value, boolean isLeaf) {
        if(!value.isEmpty() && value.charAt(0) == '(' && value.charAt(value.length()-1) == ')') {
            this.value = value.substring(1, value.length()-1);
            while(this.value.charAt(0) == '(' && this.value.charAt(this.value.length()-1) == ')') {
                this.value = this.value.substring(1, this.value.length()-1);
            }
        } else {
            this.value = value;
        }
        this.isLeaf = isLeaf;
        parent = null;
        if(!isLeaf) {
            children = new FunctionTreeNode[2];
        }
        tempValue = "";
        totalValue = null;
    }
    
    public void addChild(FunctionTreeNode node) {
        if(!isLeaf) {
            if(children[0] == null) {
                children[0] = node;
            } else {
                children[1] = node;
            }
            node.parent = this;
        }
    }
    
    public FunctionTreeNode getParent() {
        return parent;
    }
    
    public FunctionTreeNode getLeftChild() {
        if(isLeaf) {
            return null;
        }
        return children[0];
    }
    
    public FunctionTreeNode getRightChild() {
        if(isLeaf) {
            return null;
        }
        return children[1];
    }
    
    public boolean hasRightChild() {
        return children != null && children[1] != null;
    }
    
    public boolean isLeaf() {
        return isLeaf;
    }
    
    public String getValue() {
        return value;
    }
    
    public String getTotalValue() {
        if(totalValue == null) {
            if(isLeaf) {
                totalValue = value;
            } else if(children[1] == null) {
                totalValue = value +"(" +children[0].getTotalValue() +")";
            } else {
                totalValue = "(" +children[0].getTotalValue() +")" +value +"(" +children[1].getTotalValue() +")";
            }
        }
        return totalValue;
    }
    
    public void print() {
        FunctionTreeNode[] prevLayer = {this};
        System.out.println("0:" +value);
        ArrayList<FunctionTreeNode> layer = new ArrayList<>();
        if(!isLeaf) {
            layer.add(children[0]);
            if(children[1] != null) {
                layer.add(children[1]);
            }
            while(layer.size() > 0) {
                for(int i = 0; i < layer.size(); i++) {
                    for(int j = 0; j < prevLayer.length; j++) {
                        if(layer.get(i).getParent() == prevLayer[j]) {
                            System.out.print(j +"->");
                            break;
                        }
                    }
                    System.out.print(i +":" +layer.get(i).value +" ");
                }
                System.out.println();
                prevLayer = layer.toArray(prevLayer);
                layer.clear();
                for(int i = 0; i < prevLayer.length; i++) {
                    if(!prevLayer[i].isLeaf) {
                        layer.add(prevLayer[i].children[0]);
                        if(prevLayer[i].children[1] != null) {
                            layer.add(prevLayer[i].children[1]);
                        }
                    }
                }
            }
        }
    }
    
    public int split(String variable) {
        if(value.compareTo(variable) == 0) {
            return 0;
        } else if(isDouble(value)) {
            return 0;
        }
        //check for addition & subtraction
        int index = MyMath.nonNegativeMin(value.indexOf("+"), value.indexOf("-"));
        if(index >= 0) {
            int index1 = index;
            int open = 0;
            while(index1 > 0 && (open != 0 || value.charAt(index1-1) != '(')) {
                index1--;
                if(value.charAt(index1) == ')') {
                    open--;
                } else if(value.charAt(index1) == '(') {
                    open++;
                }
            }
            while(index >= 0 && index1 > 0) {
                index = MyMath.nonNegativeMin(value.indexOf("+",index+1), value.indexOf("-",index+1));
                index1 = index;
                open = 0;
                while(index1 > 0 && (open != 0 || value.charAt(index1-1) != '(')) {
                    index1--;
                    if(value.charAt(index1) == ')') {
                        open--;
                    } else if(value.charAt(index1) == '(') {
                        open++;
                    }
                }
            }
            if(index >= 0) {
                isLeaf = false;
                children = new FunctionTreeNode[2];
                addChild(new FunctionTreeNode(value.substring(0, index), true));
                addChild(new FunctionTreeNode(value.substring(index+1), true));
                value = "" +value.charAt(index);
                return 2;
            }
        }
        //check for multiplication & division
        index = MyMath.nonNegativeMin(value.indexOf("*"), value.indexOf("/"));
        if(index >= 0) {
            int index1 = index;
            int open = 0;
            while(index1 > 0 && (open != 0 || value.charAt(index1-1) != '(') && (open != 0 || (value.charAt(index1-1) != '+' && value.charAt(index1-1) != '-'))) {
                index1--;
                if(value.charAt(index1) == ')') {
                    open--;
                } else if(value.charAt(index1) == '(') {
                    open++;
                }
            }
            while(index >= 0 && index1 > 0) {
                index = MyMath.nonNegativeMin(value.indexOf("*", index+1), value.indexOf("/", index+1));
                index1 = index;
                open = 0;
                while(index1 > 0 && (open != 0 || value.charAt(index1-1) != '(') && (open != 0 || (value.charAt(index1-1) != '+' && value.charAt(index1-1) != '-'))) {
                    index1--;
                    if(value.charAt(index1) == ')') {
                        open--;
                    } else if(value.charAt(index1) == '(') {
                        open++;
                    }
                }
            }
            if(index >= 0) {
                isLeaf = false;
                children = new FunctionTreeNode[2];
                addChild(new FunctionTreeNode(value.substring(0, index), true));
                addChild(new FunctionTreeNode(value.substring(index+1), true));
                value = "" +value.charAt(index);
                return 2;
            }
        }
        
        //check for powers
        index = value.indexOf("^");
        if(index >= 0) {
            int index1 = index;
            int open = 0;
            while(index1 > 0 && (open != 0 || value.charAt(index1-1) != '(') && (open != 0 || (value.charAt(index1-1) != '+' && value.charAt(index1-1) != '-' && value.charAt(index1-1) != '*' && value.charAt(index1-1) != '/'))) {
                index1--;
                if(value.charAt(index1) == ')') {
                    open--;
                } else if(value.charAt(index1) == '(') {
                    open++;
                }
            }
            while(index >= 0 && index1 > 0) {
                index = value.indexOf("^", index+1);
                index1 = index;
                open = 0;
                while(index1 > 0 && (open != 0 || value.charAt(index1-1) != '(') && (open != 0 || (value.charAt(index1-1) != '+' && value.charAt(index1-1) != '-' && value.charAt(index1-1) != '*' && value.charAt(index1-1) != '/'))) {
                    index1--;
                    if(value.charAt(index1) == ')') {
                        open--;
                    } else if(value.charAt(index1) == '(') {
                        open++;
                    }
                }
            }
            if(index >= 0) {
                isLeaf = false;
                children = new FunctionTreeNode[2];
                addChild(new FunctionTreeNode(value.substring(0, index), true));
                addChild(new FunctionTreeNode(value.substring(index+1), true));
                value = "" +value.charAt(index);
                return 2;
            }
        }
        
        //check for functions
        for(int i = 0; i < Calculator.functions.length; i++) {
            if(value.matches(Calculator.functions[i] +"\\([\\d\\.\\w\\^\\+\\-\\*/]+\\)")) {
                isLeaf = false;
                children = new FunctionTreeNode[2];
                addChild(new FunctionTreeNode(value.substring(Calculator.functions[i].length()), true));
                value = Calculator.functions[i];
                return 1;
            }
        }
        throw new GraphingException("Couldn't split \"" +value +"\"");
    }

    private static boolean isDouble(String value) {
        try {
            Calculator.myParseDouble(value);
            return true;
        } catch(NumberFormatException ex) {
            for(int i = 0; i < Calculator.constants.length; i++) {
                if(value.compareTo(Calculator.constants[i]) == 0) {
                    return true;
                }
            }
            return false;
        }
    }
    
}
