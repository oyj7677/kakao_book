package com.oyj.kakaobook.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oyj.kakaobook.data.SearchSortCriteria
import com.oyj.kakaobook.ui.component.ComponentConstants.Padding

@Composable
fun SortCriteriaSelector(
    selectedCriteria: SearchSortCriteria,
    sortCriteriaList: List<SearchSortCriteria>,
    onCriteriaSelected: (SearchSortCriteria) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Padding.SMALL)
    ) {
        sortCriteriaList.forEach { criteria ->
            FilterChip(
                onClick = { onCriteriaSelected(criteria) },
                label = { Text(criteria.displayName) },
                selected = selectedCriteria == criteria
            )
        }
    }
}

@Preview
@Composable
private fun SortCriteriaSelectorPreview() {
    SortCriteriaSelector(
        selectedCriteria = SearchSortCriteria.Accuracy,
        sortCriteriaList = SearchSortCriteria.getSearchCriteria(),
        onCriteriaSelected = {}
    )
}
