package com.lili.school.manager;

import java.util.List;

import com.lili.cms.entity.PagedResult;
import com.lili.school.model.CommentTag;

public interface ICommentTagManager {

	PagedResult<CommentTag> queryCommentTagList(CommentTag commentTag);

	List<CommentTag> queryComTagList(String id);


}
