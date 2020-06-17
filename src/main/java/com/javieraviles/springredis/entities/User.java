package com.javieraviles.springredis.entities;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class User implements Serializable {
	private static final long serialVersionUID = -305726463442998985L;
	private String id;
	private String name;
	private String email;
}
