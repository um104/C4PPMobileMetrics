package edu.channel4.mm.db.android.database;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.channel4.mm.db.android.model.description.AppDescription;
import edu.channel4.mm.db.android.model.description.AttributeDescription;
import edu.channel4.mm.db.android.model.description.EventDescription;
import edu.channel4.mm.db.android.model.description.EventNameDescription;
import edu.channel4.mm.db.android.model.graph.Graph;
import edu.channel4.mm.db.android.model.request.CustomGraphRequest;
import edu.channel4.mm.db.android.model.request.GraphRequest;

@Singleton
public class Database {

   @Inject private ArrayList<AppDescription> appDescriptions;
   @Inject private ArrayList<AttributeDescription> attributeDescriptions;
   @Inject private ArrayList<EventDescription> eventDescriptions;
   @Inject private ArrayList<EventNameDescription> eventNameDescriptions;
   @Inject private CustomGraphRequestDataSource customGraphRequestDataSource;
   private Graph graph = null;

   public void setAppDescriptions(List<AppDescription> appDescriptions) {
      this.appDescriptions.clear();
      this.appDescriptions.addAll(appDescriptions);
   }

   public void setAttributeDescriptions(
            List<AttributeDescription> attributeDescriptions) {
      this.attributeDescriptions.clear();
      this.attributeDescriptions.addAll(attributeDescriptions);
   }

   public void setEventDescriptions(List<EventDescription> eventDescriptions) {
      this.eventDescriptions.clear();
      this.eventDescriptions.addAll(eventDescriptions);
   }

   public void setEventNameDescriptions(
            List<EventNameDescription> eventNameDescriptions) {
      this.eventNameDescriptions.clear();
      this.eventNameDescriptions.addAll(eventNameDescriptions);
   }

   public void addCustomGraphRequest(CustomGraphRequest customGraphRequest,
            String appId) {
      customGraphRequestDataSource.open();

      customGraphRequestDataSource.insertCustomGraphRequest(customGraphRequest,
               appId);

      customGraphRequestDataSource.close();
   }

   public List<GraphRequest> getCustomGraphRequests(String appId) {
      customGraphRequestDataSource.open();

      List<GraphRequest> customGraphRequests = customGraphRequestDataSource
               .getAllGraphRequests(appId);

      customGraphRequestDataSource.close();

      return customGraphRequests;
   }

   public void setGraph(Graph graph) {
      this.graph = graph;
   }

   public Graph getGraph() {
      return graph;
   }
}
