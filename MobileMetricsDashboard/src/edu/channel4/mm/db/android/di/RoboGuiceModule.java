package edu.channel4.mm.db.android.di;

import android.content.Context;

import com.google.inject.AbstractModule;

/**
 * Define Interface -> Implementation injection bindings here.
 * 
 * Side note: Guice won't let you bind Java generics without specifying a type,
 * for type-safety.
 */
public class RoboGuiceModule extends AbstractModule {

   protected Context context;

   public RoboGuiceModule(Context context) {
      this.context = context;
   }

   @Override
   public void configure() {

//      // @Inject List<String>
//      bind(new TypeLiteral<List<String>>() {
//      }).toInstance(new ArrayList<String>());
//
//      // @Inject List<GraphRequest>
//      bind(new TypeLiteral<List<GraphRequest>>() {
//      }).toInstance(new ArrayList<GraphRequest>());
//
//      // @Inject List<GraphType>
//      bind(new TypeLiteral<List<GraphType>>() {
//      }).toInstance(new ArrayList<GraphType>());
//
//      // @Inject List<AppDescription>
//      bind(new TypeLiteral<List<AppDescription>>() {
//      }).toInstance(new ArrayList<AppDescription>());
//
//      // @Inject List<AttributeDescription>
//      bind(new TypeLiteral<List<AttributeDescription>>() {
//      }).toInstance(new ArrayList<AttributeDescription>());
//
//      // @Inject List<EventDescription>
//      bind(new TypeLiteral<List<EventDescription>>() {
//      }).toInstance(new ArrayList<EventDescription>());
//
//      // @Inject List<EventNameDescription>
//      bind(new TypeLiteral<List<EventNameDescription>>() {
//      }).toInstance(new ArrayList<EventNameDescription>());

   }

}
