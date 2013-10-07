/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.strato.service;

import org.springframework.data.domain.Page;
import wad.strato.domain.Observation;

/**
 *
 * @author timosand
 */
public interface ObservationService {

    public Page<Observation> list(Integer page);
}
