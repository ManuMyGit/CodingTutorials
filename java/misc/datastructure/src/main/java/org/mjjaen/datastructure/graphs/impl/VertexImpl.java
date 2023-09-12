package org.mjjaen.datastructure.graphs.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.mjjaen.datastructure.graphs.Vertex;

@Data
@AllArgsConstructor
public class VertexImpl<T> implements Vertex<T> {
    private T value;
}