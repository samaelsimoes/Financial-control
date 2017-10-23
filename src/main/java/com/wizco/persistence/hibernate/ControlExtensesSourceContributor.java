package com.wizco.persistence.hibernate;

import java.util.Iterator;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.spi.MetadataSourcesContributor;
import org.reflections.Reflections;

public class ControlExtensesSourceContributor implements MetadataSourcesContributor {

	@Override
	public void contribute(MetadataSources metadataSources) {
		try {
			Reflections reflections = new Reflections("com.wizco.entity");
			Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Entity.class);
			annotated.addAll(reflections.getTypesAnnotatedWith(Embeddable.class));
			annotated.addAll(reflections.getTypesAnnotatedWith(MappedSuperclass.class));
			annotated.addAll(reflections.getTypesAnnotatedWith(Filter.class));
			annotated.addAll(reflections.getTypesAnnotatedWith(FilterDef.class));
			Iterator<Class<?>> iterator = annotated.iterator();
			while (iterator.hasNext()) {
				Class class1 = iterator.next();
				if (class1.getName().startsWith("com.wizco.entity")) {
					metadataSources.addAnnotatedClass(class1);
				}
			}
		} catch (SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

}
