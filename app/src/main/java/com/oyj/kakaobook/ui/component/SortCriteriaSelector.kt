package com.oyj.kakaobook.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oyj.kakaobook.model.SortCriteria

@Composable
fun SortCriteriaSelector(
    selectedCriteria: SortCriteria,
    onCriteriaSelected: (SortCriteria) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SortCriteria.getAllCriteria().forEach { criteria ->
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
        selectedCriteria = SortCriteria.Accuracy,
        onCriteriaSelected = {}
    )
}
