package edu.ucsb.cs56.ride2school.config;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucsb.cs56.ride2school.data.PostData;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

public class WebConfig {

	private DatabaseConfig db;

	public WebConfig(DatabaseConfig db) {
		this.db = db;
		SetUpRoutes();
	}

	private void SetUpRoutes() {

		get("/", (rq, rs) -> {
			Map<String, Object> map = new HashMap<>();
			map.put("posts", getPosts());
			return new ModelAndView(map, "feed.mustache");
		}, new MustacheTemplateEngine());

		get("/form/post", (rq, rs) -> {
			Map<String, Object> map = new HashMap<>();
			return new ModelAndView(map, "post.mustache");
		}, new MustacheTemplateEngine());

		get("/login", (rq, rs) -> {
			Map<String, Object> map = new HashMap<>();
			return new ModelAndView(map, "login.mustache");
		}, new MustacheTemplateEngine());
	}

	private List<PostData> getPosts() {
		List<PostData> posts = db.getAllPosts();
		posts.sort((p1, p2) -> p1.getLastUpdate().compareTo(p2.getLastUpdate()));
		return posts;
	}
}