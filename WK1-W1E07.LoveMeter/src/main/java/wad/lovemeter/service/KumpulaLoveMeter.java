/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.lovemeter.service;

/**
 *
 * @author timosand
 */
public class KumpulaLoveMeter implements LoveMeterService {

    @Override
    public int match(String name1, String name2) {
        int minLength = Math.min(name1.length(), name2.length());

        int result = 0;
        for (int i = 0; i < minLength; i++) {
            result += (name1.charAt(i) * name2.charAt(i));
        }

        result += 42;

        return result % 100;
    }
}
