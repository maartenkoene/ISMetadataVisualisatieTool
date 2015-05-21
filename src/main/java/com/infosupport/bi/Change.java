/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

/**
 *
 * @author MaartenKo
 */
public interface Change {

    public boolean inverse(Change candidate);
    
}
