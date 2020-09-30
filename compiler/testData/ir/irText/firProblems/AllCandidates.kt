// FILE: OverloadResolutionResultsImpl.java

import java.util.*;

public class OverloadResolutionResultsImpl<D> {
    public Collection<ResolvedCall<D>> getAllCandidates() {
        return Collections.emptyList();
    }

    public void setAllCandidates(Collection<ResolvedCall<D>> allCandidates) {
    }

    public static <D> OverloadResolutionResultsImpl<D> nameNotFound() {
        OverloadResolutionResultsImpl<D> results = new OverloadResolutionResultsImpl<>();
        results.setAllCandidates(Collections.emptyList());
        return results;
    }
}

// FILE: AllCandidates.kt
// WITH_RUNTIME
// FULL_JDK

class ResolvedCall<D>

class MyCandidate(val resolvedCall: ResolvedCall<*>)

private fun <D> allCandidatesResult(allCandidates: Collection<MyCandidate>) =
    OverloadResolutionResultsImpl.nameNotFound<D>().apply {
        this.allCandidates = allCandidates.map {
            it.resolvedCall as ResolvedCall<D>
        }
    }
