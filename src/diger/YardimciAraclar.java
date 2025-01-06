package diger;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter;
import javax.swing.text.SimpleAttributeSet;

public class YardimciAraclar {

    public static void sabitBoyutOlustur(Component component, Dimension boyut) {
        component.setMinimumSize(boyut);
        component.setMaximumSize(boyut);
        component.setPreferredSize(boyut);
    }

    // Stok alanına sadece sayı yazılması için kullanılacak
    public static void sadeceSayiyaIzinVer(JTextField textField) {
        DocumentFilter filter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attrs) {
                if (string.matches("[0-9]*")) {  // Sadece sayıları kabul et
                    try {
                        super.insertString(fb, offset, string, attrs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) {
                if (text.matches("[0-9]*")) {  // Sadece sayıları kabul et
                    try {
                        super.replace(fb, offset, length, text, attrs != null ? attrs : new SimpleAttributeSet());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        if (textField.getDocument() instanceof AbstractDocument) {
            ((AbstractDocument) textField.getDocument()).setDocumentFilter(filter);
        }
    }

    // Fiyat alanına sadece ondalık sayı yazılması için kullanılacak
    public static void sadeceOndalikSayiyaIzinVer(JTextField textField) {
        DocumentFilter filter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attrs) {
                // Sayı veya bir adet nokta kabul et
                if (string.matches("[0-9]*[.]?[0-9]*")) {
                    try {
                        super.insertString(fb, offset, string, attrs);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) {
                // Sayı veya bir adet nokta kabul et
                if (text.matches("[0-9]*[.]?[0-9]*")) {
                    try {
                        super.replace(fb, offset, length, text, attrs != null ? attrs : new SimpleAttributeSet());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        if (textField.getDocument() instanceof AbstractDocument) {
            ((AbstractDocument) textField.getDocument()).setDocumentFilter(filter);
        }
    }
}
