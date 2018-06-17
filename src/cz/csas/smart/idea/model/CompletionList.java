package cz.csas.smart.idea.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("completions")
public class CompletionList {

	@XStreamImplicit
	private List<Completion> values = new ArrayList<>();

	public void addCompletion(Completion completion) {
		values.add(completion);
	}
}
