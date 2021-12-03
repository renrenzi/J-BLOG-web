package test.com.jj.jblog.controller.blog; 

import com.jj.jblog.controller.blog.BlogCommentController;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import javax.annotation.Resource;

/** 
* BlogCommentController Tester. 
* 
* @author <Authors name> 
* @since <pre>11月 15, 2021</pre> 
* @version 1.0 
*/ 
public class BlogCommentControllerTest { 

    @Resource
    private BlogCommentController controller;
@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: pageComment(PageCondition<BlogComment> condition, BlogComment blogComment) 
* 
*/ 
@Test
public void testPageComment() throws Exception {

//TODO: Test goes here... 
} 

/** 
* 
* Method: updateCommentStatus(BlogComment blogComment) 
* 
*/ 
@Test
public void testUpdateCommentStatus() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: deleteComment(@RequestParam Long commentId) 
* 
*/ 
@Test
public void testDeleteComment() throws Exception { 
//TODO: Test goes here... 
} 


} 
