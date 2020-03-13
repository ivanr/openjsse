/*
 * Copyright 2019 Azul Systems, Inc.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.openjsse.sun.security.util;

import sun.security.util.NamedCurve;
import java.util.Collection;
import java.security.spec.*;
import java.security.spec.ECParameterSpec;

/**
 * Wrapper class for sun.security.util.CurveDB.lookup methods 
 */
public class CurveDB {

    // Return a NamedCurve for the specified OID/name or null if unknown.
    public static ECParameterSpec lookup(String name) {
        Collection<? extends NamedCurve> curves = sun.security.util.CurveDB.getSupportedCurves();
        for (NamedCurve nc : curves) {
            if (nc.getName().startsWith(name)) {
                return nc;
            }
        }
        
        return null;        
    }
    
    // Convert the given ECParameterSpec object to a NamedCurve object.
    // If params does not represent a known named curve, return null.
    public static ECParameterSpec lookup(ECParameterSpec params) {
        if ((params instanceof NamedCurve) || (params == null)) {
            return params;
        }

        int fieldSize = params.getCurve().getField().getFieldSize();
        
        for (NamedCurve namedCurve : sun.security.util.CurveDB.getSupportedCurves()) {
            if (namedCurve.getCurve().getField().getFieldSize() != fieldSize) {
                continue;
            }
            
            if (namedCurve.getCurve().equals(params.getCurve()) == false) {
                continue;
            }
            
            if (namedCurve.getGenerator().equals(params.getGenerator()) == false) {
                continue;
            }
            
            if (namedCurve.getOrder().equals(params.getOrder()) == false) {
                continue;
            }
            
            if (namedCurve.getCofactor() != params.getCofactor()) {
                continue;
            }
            
            return namedCurve;
        }
        
        return null;
    }
}
